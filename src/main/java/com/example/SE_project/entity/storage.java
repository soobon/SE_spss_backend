package com.example.SE_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "filestorage")
public class storage {
    @Id
    @Column(name = "file_id", length = 100)
    private String fileid;

    @Lob
    @Column(name = "filedata" , length =  100000)
    private byte[] filedata;
}
