package com.ahnstudio.management.dao;

import com.ahnstudio.management.pojo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("fileVoMapper")
public interface FileVOMapper {
    int deleteByPrimaryKey(Integer fileSeq);
    int insert(FileVO record);
    int insertSelective(FileVO record);
    FileVO selectByPrimaryKey(Integer fileSeq);
    int updateByPrimaryKeySelective(FileVO record);
    int updateByPrimaryKey(FileVO record);
    List<FileVO> selectTB_FILEForMain();
    int insertTB_FILEFORMain(FileVO fileVO);
    int deleteTB_FILEByUSE_TYPE(FileVO fileVO);
}