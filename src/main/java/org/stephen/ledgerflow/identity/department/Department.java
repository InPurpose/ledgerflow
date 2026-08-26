package org.stephen.ledgerflow.identity.department;

import jakarta.persistence.*;

import java.time.Instant;

import java.util.UUID;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(length=100, nullable=false)
    private String name;

    @Column(length=30, nullable=false, unique=true)
    private String code;

    @Column(name = "created_at", nullable=false)
    private Instant createdAt = Instant.now();

//  Constructors

    protected  Department() {
    }

    public Department(String name, String code) {
        this.name = name;
        this.code = code;
    }
//  Getter and Setter

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {}

    public String getName() {return name;}
    public void setName(String name) {}

    public String getCode() {return code;}
    public void setCode(String code) {}

    public Instant getCreatedAt() {return createdAt;}
    public void setCreatedAt(Instant createdAt) {this.createdAt = createdAt;}

    @Override
    public String toString(){
        return "id: " + id + "| name: " + name + "| code: " + code + "| createdAt: " + createdAt;
    }

}
