package com.example.demo.controller;

import com.example.demo.mapper.NoteMapper;
import com.example.demo.model.ApiCode;
import com.example.demo.model.Note;
import com.example.demo.model.ReturnT;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(value ="笔记", tags ="笔记接口")
public class NoteController {
    @Autowired
    private NoteMapper noteMapper;

    @ApiOperation(value = "全量更新笔记")
    @RequestMapping(value = "/addNote",method = RequestMethod.POST)
    @Transactional
    public String addNote(String jsonData,String userId){
        try{
            HashMap<String,Object> map=new HashMap<>();
            map.put("userId",userId);
            List<Note> noteList=noteMapper.selectByMap(map);

            List<String> list=new ArrayList<>();
            for(Note note:noteList){
                list.add(note.getId());
            }
            //删除原有笔记
            if(noteList.size()>0){
                noteMapper.deleteBatchIds(list);
            }

            //全量更新
            List<Note> noteList1 = new Gson().fromJson(jsonData, new TypeToken<List<Note>>(){}.getType());

            for(Note note:noteList1){
                note.setUserId(userId);
                noteMapper.insert(note);
            }
            return new Gson().toJson(ReturnT.success("新增笔记成功"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"新增笔记失败"));
    }


    @ApiOperation(value = "回收笔记/删除笔记")
    @RequestMapping(value = "/recyclingNotes",method = RequestMethod.POST)
    @Transactional
    public String recyclingNotes(String noteId,String type){
        try {
            Note note = noteMapper.selectById(noteId);
            if(null!=note){
                if ("recover".equals(type)) {
                    note.setStatus(1);
                    noteMapper.updateById(note);
                    return new Gson().toJson(ReturnT.success("回收笔记成功"));
                } else {
                    noteMapper.deleteById(noteId);
                    return new Gson().toJson(ReturnT.success("删除笔记成功"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"操作失败"));
    }

    @ApiOperation(value = "按时间查找笔记")
    @RequestMapping(value = "/findNotesByTime",method = RequestMethod.POST)
    public String findNotesByTime(String time1,String time2){
        try {
            List<Note> note = noteMapper.selectByTime(time1,time2);
            return new Gson().toJson(ReturnT.success(note));
        }catch (Exception e){
            return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"操作失败"));
        }
    }

    @ApiOperation(value = "按用户查找笔记")
    @RequestMapping(value = "/findNotesByUser",method = RequestMethod.POST)
    public String findNotesByUser(String userId){
        try {
            HashMap<String,Object> map=new HashMap<>();
            map.put("userId",userId);
            List<Note> note = noteMapper.selectByMap(map);
            return new Gson().toJson(ReturnT.success(note));
        }catch (Exception e){
            return new Gson().toJson(ReturnT.fail(ApiCode.API_noRegion,"操作失败"));
        }
    }

}
