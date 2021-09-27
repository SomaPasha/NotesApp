package space.kuz.notesapp.domain;

import androidx.annotation.Nullable;

import java.util.Date;

public class NoteStructure {
    @Nullable
    private Integer id;
    private String head;
    private String description;
    private Date date;

    public NoteStructure(@Nullable Integer id, String head, String description, Date date) {
        this.id = id;
        this.head = head;
        this.description = description;
        this.date = date;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
