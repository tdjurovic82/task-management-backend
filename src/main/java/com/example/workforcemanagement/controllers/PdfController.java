package com.example.workforcemanagement.controllers;

import com.example.workforcemanagement.dto.TaskDetailsDTO;
import com.example.workforcemanagement.services.TaskService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final TaskService taskService;

    public PdfController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(("/{taskId}"))
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long taskId) {
        TaskDetailsDTO dto = taskService.getTaskDetailsDTOById(taskId);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);


            System.out.println("Description: " + dto.getDescription());
            System.out.println("Status: " + dto.getStatus());
            System.out.println("TaskType: " + (dto.getTaskType() != null ? dto.getTaskType() : "null"));
            System.out.println("Assignee: " + dto.getAssignee());
            System.out.println("Postcode: " + dto.getPostcode());
            System.out.println("Address: " + dto.getAddress());
            System.out.println("UPRN: " + dto.getUprn());
            contentStream.beginText();
            PDType0Font.load(document,
                    getClass().getClassLoader().getResourceAsStream("fonts/LiberationSans-Regular.ttf"));

            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 700);


            // Popunjavanje PDF-a sa podacima iz DTO-a
            contentStream.showText("Task ID: " + dto.getId());
            contentStream.newLine();

            contentStream.newLine();
            contentStream.showText("Description: " + dto.getDescription());
            contentStream.newLine();
            contentStream.showText("Status: " + dto.getStatus());
            contentStream.newLine();
            contentStream.showText("Task Type: " + dto.getTaskType());
            contentStream.newLine();
            contentStream.showText("Assignee: " + (dto.getAssignee() != null ? dto.getAssignee() : "N/A"));
            contentStream.newLine();
            contentStream.showText("Postcode: " + dto.getPostcode());
            contentStream.showText("Address: " + dto.getAddress());
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("UPRN: " + dto.getUprn());
            contentStream.endText();
            contentStream.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=task-details.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
