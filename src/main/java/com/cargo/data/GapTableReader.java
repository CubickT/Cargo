package com.cargo.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GapTableReader {


    public static List<GapTableRow> createGapTable() {
        ObjectMapper mapper = new ObjectMapper();
        List<GapTableRow> table;
        InputStream path = GapTableReader.class.getResourceAsStream("gapTable.json");


        if (path == null) {
            throw new RuntimeException("Файл gapTable.json не найден в ресурсах рядом с классом GapTableReader");
        }

        {
            try {
                table = mapper.readValue(path, new TypeReference<>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (GapTableRow row : table) {
            System.out.println("Тип + " + row.getType());
        }


        return table;
    }

}
