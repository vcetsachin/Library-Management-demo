package com.knf.dev.librarymanagementsystem.service.impl;

import com.knf.dev.librarymanagementsystem.constant.Item;
import com.knf.dev.librarymanagementsystem.service.*;
import com.knf.dev.librarymanagementsystem.vo.*;
import com.knf.dev.librarymanagementsystem.util.Mapper;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

@Service
public class FileServiceImpl implements FileService {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;

    public FileServiceImpl(BookService bookService,
                           AuthorService authorService,
                           PublisherService publisherService,
                           CategoryService categoryService) {

        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.categoryService = categoryService;
    }

    @Override
    public void exportCSV(String fileName, HttpServletResponse response)
            throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {

        var item = Item.getItemByValue(fileName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid file type"));

        response.setContentType("text/csv");

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + item.getFileName() + "\"");

        PrintWriter writer = response.getWriter();

        switch (item) {

            case BOOK:
                StatefulBeanToCsv<BookRecord> bookWriter = getWriter(writer);
                bookWriter.write(Mapper.bookModelToVo(bookService.findAllBooks()));
                break;

            case AUTHOR:
                StatefulBeanToCsv<AuthorRecord> authorWriter = getWriter(writer);
                authorWriter.write(Mapper.authorModelToVo(authorService.findAllAuthors()));
                break;

            case CATEGORY:
                StatefulBeanToCsv<CategoryRecord> categoryWriter = getWriter(writer);
                categoryWriter.write(Mapper.categoryModelToVo(categoryService.findAllCategories()));
                break;

            case PUBLISHER:
                StatefulBeanToCsv<PublisherRecord> publisherWriter = getWriter(writer);
                publisherWriter.write(Mapper.publisherModelToVo(publisherService.findAllPublishers()));
                break;

            default:
                throw new IllegalArgumentException("Unsupported file type");
        }
    }

    private static <T> StatefulBeanToCsv<T> getWriter(PrintWriter printWriter) {

        return new StatefulBeanToCsvBuilder<T>(printWriter)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
    }
}