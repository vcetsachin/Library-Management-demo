package com.knf.dev.librarymanagementsystem.service;

import jakarta.servlet.http.HttpServletResponse;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;

public interface FileService {

    void exportCSV(String fileName, HttpServletResponse response)
            throws CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException,
            IOException;

}