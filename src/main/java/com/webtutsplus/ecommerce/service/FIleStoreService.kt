package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.config.StorageProperties
import com.webtutsplus.ecommerce.exceptions.StorageException
import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Stream

@Service
class FIleStoreService {
    private val properties = StorageProperties()
    var rootLocation = Paths.get(properties.location)
    fun store(file: MultipartFile): String {
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }
            val extension = FilenameUtils.getExtension(file.originalFilename)
            val uploadedFileName = UUID.randomUUID().toString() + "." + extension
            val destinationFile = rootLocation.resolve(
                Paths.get(uploadedFileName)
            )
                .normalize().toAbsolutePath()
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
                val baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
                return "$baseUrl/fileUpload/files/$uploadedFileName"
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
    }

    fun loadAll(): Stream<Path> {
        return try {
            Files.walk(rootLocation, 1)
                .filter { path: Path -> path != rootLocation }
                .map { other: Path? -> rootLocation.relativize(other) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    fun load(filename: String?): Resource {
        return try {
            val file = rootLocation.resolve(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw RuntimeException("Could not read the file!")
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException("Error: " + e.message)
        }
    }
}