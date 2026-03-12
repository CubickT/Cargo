package com.cargo.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GapTableReader {


    public static Map<String, GapTableRow> createGapTable() {
        ObjectMapper mapper = new ObjectMapper();
        List<GapTableRow> table;

        try (InputStream path = GapTableReader.class.getResourceAsStream("gapTable.json")) {
            if (path == null) {
                throw new RuntimeException("Файл gapTable.json не найден");
            }
            table = mapper.readValue(path, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, GapTableRow> gapTable = new HashMap<>();

        for(GapTableRow row : table){
            String key = row.getType() + "|" + row.getHeightIndex();
            gapTable.put(key,row);
        }


        return gapTable;
    }

}
