package com.reebu.pokerhand.parser

import java.io.{BufferedReader, BufferedWriter, File, FileInputStream, FileOutputStream, InputStreamReader, OutputStreamWriter, Writer}
import javax.management.RuntimeMBeanException

object FileReader {
  def readerReadLines(path: String, klass: Class[_]) = {
    val inputStream = klass.getResourceAsStream(path)
    val reader = new BufferedReader(new InputStreamReader(inputStream))

    def readLines(reader: BufferedReader): List[String] = {
      def readLinesHelper(list: List[String]): List[String] = {
        val line = reader.readLine()
        if (line != null) {
          readLinesHelper(line :: list)
        } else {
          reader.close()
          list.reverse
        }
      }

      readLinesHelper(List.empty)
    }

    readLines(reader)
  }
}
