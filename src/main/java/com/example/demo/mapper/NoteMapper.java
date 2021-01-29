package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Note;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface NoteMapper extends BaseMapper<Note> {
    List<Note> selectByTime(@Param("time1") String time1,@Param("time2") String time2);
}
