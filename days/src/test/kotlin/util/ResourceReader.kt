package util

import java.nio.file.Files
import java.nio.file.Paths

private class ResourceReader {

}

fun readPuzzle(str: String): List<String> {
    val path = Paths.get(ResourceReader::javaClass.javaClass.getResource("/${str}_puzzle_input.txt").toURI())
    return Files.readAllLines(path)
}