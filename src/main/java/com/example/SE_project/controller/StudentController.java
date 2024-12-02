package com.example.SE_project.controller;

import com.example.SE_project.dto.NewFileDTO;
import com.example.SE_project.dto.SendRequestDTO;
import com.example.SE_project.dto.StudentDTO;
import com.example.SE_project.dto.requestDTO;
import com.example.SE_project.entity.Student;
import com.example.SE_project.entity.key.PrintKey;
import com.example.SE_project.service.AdminService;
import com.example.SE_project.service.StudentService;
import com.example.SE_project.service.storageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("/student")
@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class StudentController {

    private StudentService studentService ;

    private AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> allStudent(){
        return new ResponseEntity<>(studentService.allStudents(), HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getInfo(@PathVariable String id){
        return new ResponseEntity<>(studentService.getInfo(id),HttpStatus.OK);
    }

//    @GetMapping("/statistic/{id}/{month}")
//    public ResponseEntity<?> getStatistic(@PathVariable String id,@PathVariable Integer month){
//        return new ResponseEntity<>(studentService.getStatistic(id,month),HttpStatus.OK);
//    }

    @GetMapping("/statistic/{id}/{year}")
    public ResponseEntity<?> getStatistic(@PathVariable String id,@PathVariable Integer year){
        return new ResponseEntity<>(studentService.getStatistic(id,year),HttpStatus.OK);
    }

    @GetMapping("/file_list/{id}")
    public ResponseEntity<?> getFiles(@PathVariable String id){
        return new ResponseEntity<>(studentService.getFiles(id),HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getHistory(@PathVariable String id){
        return new ResponseEntity<>(studentService.getHistory(id),HttpStatus.OK);
    }

    @PostMapping("/printRequest/{id}")
    public ResponseEntity<?> sendPrintRequest(
            @RequestParam String printer_id,
            @RequestParam String file_id,
            @RequestParam String paper_size,
            @RequestParam Integer one_or_two_side,
            @RequestParam Integer nb_of_copy,
            @RequestParam(name = "start_page") Integer start_page,
            @RequestParam(name = "end_page") Integer end_page,
            @RequestParam(name = "list_page") List<Integer> page_list,
            @PathVariable String id
    ){
        SendRequestDTO sendRequestDTO = SendRequestDTO.builder()
                .printer_id(printer_id)
                .file_id(file_id)
                .paper_size(paper_size)
                .one_or_two_side(one_or_two_side)
                .nb_of_copy(nb_of_copy)
                .build();
        requestDTO result = studentService.sendPrintRequest(sendRequestDTO,id,start_page,end_page,page_list);
        if (result == null) return new ResponseEntity<>("Number of pages left are not enough, please buy more page!",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/newFile/{id}")
    public ResponseEntity<?> addNewFile(@PathVariable String id,
                                        @RequestParam("numpages") int num_pages,
                                        @RequestParam("file_name") String filename ,
                                        @RequestParam("file") MultipartFile file
    ){
        return new ResponseEntity<>(studentService.addNewFile(id, new NewFileDTO(filename,num_pages) , file),HttpStatus.OK);
    }

    @DeleteMapping("/deleteFile/{file_id}")
    public ResponseEntity<?> deleteFile(@PathVariable String file_id
    ){
        return new ResponseEntity<>(studentService.deleteFile(file_id),HttpStatus.OK);
    }

    private storageService storageService ;
    @PostMapping("/addRealFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("file_id") String fileId) {
        try {
            String response = storageService.uploadFile(file, fileId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void saveFile(byte[] fileData, String filePath) throws IOException {
        // Tạo một FileOutputStream để ghi byte array vào tệp
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileData);  // Ghi byte array vào tệp
            System.out.println("File saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error saving file", e);
        }
    }

    @DeleteMapping("/deleteRequest")
    public ResponseEntity<?> deleteRequest(
            @RequestParam(name = "order_num") Integer orderNum,
            @RequestParam(name = "file_id") String file_id
    ){
        return new ResponseEntity<>(studentService.deletePrintRequest(new PrintKey(orderNum, file_id)),HttpStatus.OK);
    }

    @GetMapping("/getAllPrinter")
    public ResponseEntity<?> getAllPrinter(){
        return new ResponseEntity<>(studentService.getAllAvailablePrinter(),HttpStatus.OK);
    }

}