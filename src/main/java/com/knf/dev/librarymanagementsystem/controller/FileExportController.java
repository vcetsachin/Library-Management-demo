package com.knf.dev.librarymanagementsystem.controller;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.knf.dev.librarymanagementsystem.service.FileService;

import java.io.IOException;

@Controller
public class FileExportController {

    private final FileService fileService;

    public FileExportController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/export/{fileName}")
    public void exportCSV(@PathVariable String fileName,
                          HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        fileService.exportCSV(fileName, response);
    }
}