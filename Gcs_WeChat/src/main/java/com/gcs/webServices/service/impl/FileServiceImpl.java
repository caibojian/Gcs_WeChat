package com.gcs.webServices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.sysmgr.service.impl.GenericManagerImpl;
import com.gcs.webServices.dao.FileDao;
import com.gcs.webServices.entity.File;
import com.gcs.webServices.service.FileService;

@Service
public class FileServiceImpl  extends
	GenericManagerImpl<File, FileDao> implements FileService {
	
	@Autowired
	FileDao fileDao;

}
