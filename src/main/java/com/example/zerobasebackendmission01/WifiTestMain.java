package com.example.zerobasebackendmission01;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class WifiTestMain {
    public static void main(String[] args) throws SQLException, IOException, ExecutionException, InterruptedException {
        System.out.println(WifiService.WifiInsertAll());
    }
}
