package org.zerock.club.service;

import org.zerock.club.dto.NoteDTO;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.Note;

import java.util.List;

public interface NoteService {
    Long register(NoteDTO note);
    NoteDTO get(Long id);
    void modify(NoteDTO note);
    void remove(Long id);
    List<NoteDTO> getAllWithWriter(String writerEmail);
    default Note dtoToEntity(NoteDTO dto) {
        Note note = Note.builder()
                .num(dto.getNum())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(ClubMember.builder().email(dto.getWriterEmail()).build()).build();
        return note;
    }
    default NoteDTO entityToDTO(Note note) {
        NoteDTO noteDTO = NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();
        return noteDTO;
    }
}
