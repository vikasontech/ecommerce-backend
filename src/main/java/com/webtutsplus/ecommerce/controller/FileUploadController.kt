package com.webtutsplus.ecommerce.controller

import com.webtutsplus.ecommerce.model.FileInfo
import com.webtutsplus.ecommerce.service.FIleStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.nio.file.Path
import java.util.stream.Collectors

@RestController
@RequestMapping("/fileUpload")
class FileUploadController {

    @Autowired
    private lateinit var fileStoreService: FIleStoreService

    @PostMapping("/")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile): String {
        return fileStoreService!!.store(file)
    }

    @get:GetMapping("/")
    val listFiles: ResponseEntity<List<FileInfo>>
        get() {
            val fileInfos = fileStoreService!!.loadAll().map { path: Path ->
                val filename = path.fileName.toString()
                val url = MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController::class.java, "getFile", path.fileName.toString()).build()
                    .toString()
                FileInfo(filename, url)
            }.collect(Collectors.toList())
            val pathStream = fileStoreService!!.loadAll()
            return ResponseEntity.status(HttpStatus.OK).body(fileInfos)
        }

    @GetMapping("/files/{filename:.+}")
    fun getFile(@PathVariable filename: String?): ResponseEntity<Resource> {
        val file = fileStoreService!!.load(filename)
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.filename + "\"").body(file)
    }
}