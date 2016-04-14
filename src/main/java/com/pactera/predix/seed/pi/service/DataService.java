package com.pactera.predix.seed.pi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.predix.seed.pi.bean.Item;
import com.pactera.predix.seed.pi.bean.ItemField;
import com.pactera.predix.seed.pi.bean.PageParams;
import com.pactera.predix.seed.pi.dao.DataDao;

@RestController
public class DataService {
	
	@Autowired
	private DataDao dataDao;
	
	//{"page":1,"pageSize":20,"from":"2015-01-01","to":"2016-01-01"}
	@RequestMapping(value = "/api/pi/quality", method = RequestMethod.POST, produces = { "application/json" })
	public Item getPiData(@RequestBody PageParams param){
		Item item = new Item();
		item.setTotal(dataDao.queryPiCount(param));
		item.setItems(dataDao.queryPiList(param));
		return item;
	}
	//{"code":"100211587","value":1}
	@RequestMapping(value = "/api/pi/register", method = RequestMethod.POST, produces = { "application/json" })
	public String savePiRegister(@RequestBody ItemField param){
		dataDao.saveItemRegister(param);
		return "OK";
	}
}
