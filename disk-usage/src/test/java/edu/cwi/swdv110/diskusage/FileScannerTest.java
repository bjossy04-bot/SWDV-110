package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileScannerTest {

    @TempDir
    Path tempDir;

    private File root;

    @BeforeEach
    void setUp() throws IOException {
        Path docs = Files.createDirectories(tempDir.resolve("docs/img"));
        Files.createDirectories(tempDir.resolve("empty"));

        Files.write(tempDir.resolve("a.txt"), new byte[100]);
        Files.write(tempDir.resolve("b.log"), new byte[50]);
        Files.write(tempDir.resolve("docs/notes.txt"), new byte[300]);
        Files.write(docs.resolve("cat.png"), new byte[2000]);

        root = tempDir.toFile();
    }
    
    @Test
    void totalSize() {
        assertEquals(2450, FileScanner.totalSize(root));
        assertEquals(2300, FileScanner.totalSize(new File(root, "docs")));
        assertEquals(100, FileScanner.totalSize(new File(root, "a.txt")));
        assertEquals(0, FileScanner.totalSize(new File(root, "empty")));
    }

    @Test
    void countFiles() {
        assertEquals(4, FileScanner.countFiles(root));
        assertEquals(1, FileScanner.countFiles(new File(root, "a.txt")));
        assertEquals(0, FileScanner.countFiles(new File(root, "empty")));
    }

    @Test
    void maxDepth() {
        assertEquals(3, FileScanner.maxDepth(root));
        assertEquals(2, FileScanner.maxDepth(new File(root, "docs")));
        assertEquals(1, FileScanner.maxDepth(new File(root, "docs/img")));
        assertEquals(0, FileScanner.maxDepth(new File(root, "empty")));
        assertEquals(0, FileScanner.maxDepth(new File(root, "a.txt")));
    }

    @Test
    void listAllUsesRelativePathsInNameOrder() {
        assertEquals(List.of(new FileInfo("a.txt", 100), new FileInfo("b.log", 
        50), new FileInfo("docs/img/cat.png", 2000), new FileInfo("docs/notes.txt", 
        300)), FileScanner.listAll(root));
    }

    @Test
    void findByExtension() {
        assertEquals(List.of(new FileInfo("a.txt", 100), new FileInfo("docs/notes.txt", 
        300)), FileScanner.findByExtension(root, "TXT"));
        assertEquals(List.of(), FileScanner.findByExtension(root, "java"));
    }

    @Test
    void humanSize() {
        assertEquals("0 B", FileScanner.humanSize(0));
        assertEquals("999 B", FileScanner.humanSize(999));
        assertEquals("1.0 KB", FileScanner.humanSize(1024));
        assertEquals("1.5 KB", FileScanner.humanSize(1536));
        assertEquals("1.0 MB", FileScanner.humanSize(1024 * 1024));
        assertEquals("2.5 GB", FileScanner.humanSize(2_684_354_560L));
    }

    @Test
    void treeFullDepth() {
        String nl = System.lineSeparator();
        String s1 = "  ".repeat(1);
        String s2 = "  ".repeat(2);
        String s3 = "  ".repeat(3);

        String expected = root.getName() + "/ (2.4 KB)" + nl + s1 + "a.txt (100 B)" + nl 
        + s1 + "b.log (50 B)" + nl + s1 + "docs/ (2.2 KB)" + nl + s2 + "img/ (2.0 KB)" + nl 
        + s3 + "cat.png (2.0 KB)" + nl + s2 + "notes.txt (300 B)" + nl + s1 + "empty/ (0 B)" + nl;

        assertEquals(expected, FileScanner.tree(root, 10));
    }

    @Test
    void treeStopsAtMaxDepth() {
        String nl = System.lineSeparator();
        String s1 = "  ".repeat(1);

        String expected = root.getName() + "/ (2.4 KB)" + nl + s1 + "a.txt (100 B)" + nl 
        + s1 + "b.log (50 B)" + nl + s1 + "docs/ (2.2 KB)" + nl + s1 + "empty/ (0 B)" + nl;

        assertEquals(expected, FileScanner.tree(root, 1));
    }

}


