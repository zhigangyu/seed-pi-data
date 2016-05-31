package com.pactera.predix.seed.pi.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pactera.predix.seed.pi.bean.DataNode;
import com.pactera.predix.seed.pi.bean.Dht;
import com.pactera.predix.seed.pi.bean.Item;
import com.pactera.predix.seed.pi.bean.ItemField;
import com.pactera.predix.seed.pi.bean.PageParams;
import com.pactera.predix.seed.pi.dao.DataDao;

@RestController
public class DataService {

	private static Log _logger = LogFactory.getLog(DataService.class);

	// //////// URI Paths //////////
	/** Version path for the supported REST APIs */
	public static final String URI_PATH_VERSION = "/v1"; //$NON-NLS-1$
	/** REST request path for the welcome message */
	public static final String URI_PATH_WELCOME = URI_PATH_VERSION + "/welcome"; //$NON-NLS-1$
	/** REST request path for saving data to RDBMS or messaging queues. */
	public static final String URI_PATH_SAVE = URI_PATH_VERSION + "/save"; //$NON-NLS-1$
	/** REST request path for retrieving data from RDBMS. */
	public static final String URI_PATH_RETRIEVE = URI_PATH_VERSION + "/retrieve"; //$NON-NLS-1$

	// //////// Request Parameter Names //////////
	// These parameter names need to be in sync with HTTP River.
	private static final String PARAM_TRANSFER_ID = "transferId"; //$NON-NLS-1$
	private static final String PARAM_RIVER_NAME = "riverName"; //$NON-NLS-1$
	private static final String PARAM_CONTENT_TYPE = "contentType"; //$NON-NLS-1$
	private static final String PARAM_CONTENT_DISPOSITION = "contentDisposition"; //$NON-NLS-1$
	private static final String PARAM_CONTENT_DESCRIPTION = "contentDescription"; //$NON-NLS-1$
	private static final String PARAM_TIMESTAMP = "timestamp"; //$NON-NLS-1$
	private static final String PARAM_DATA = "data";

	@Autowired
	private DataDao dataDao;

	// {"page":1,"pageSize":20,"from":"2015-01-01","to":"2016-01-01"}
	@RequestMapping(value = "/api/pi/dht", method = RequestMethod.POST, produces = { "application/json" })
	public Item<Dht> getPiData(@RequestBody PageParams param) {
		Item<Dht> item = new Item<Dht>();
		item.setTotal(dataDao.queryDhtCount(param));
		item.setItems(dataDao.queryDhtList(param));
		return item;
	}


	/**
	 * Saves the received data into an RDBMS. Maximum size of data is restricted
	 * by database column size (MySQL.LongBlob 4GB). Data of zero size OK.
	 * 
	 * @param transferId
	 *            UUID of the transfer.
	 * @param riverName
	 *            Name of the sending river.
	 * @param contentType
	 *            MIME type of the data content.
	 * @param contentDisposition
	 *            Disposition of data content.
	 * @param contentDescription
	 *            Description of data content.
	 * @param timestamp
	 *            Date and time when data is sent.
	 * @param data
	 *            Data received.
	 * @return response message
	 * @throws Exception
	 *             if data cannot be accessed correctly.
	 */
	@ResponseBody
	@RequestMapping(value = URI_PATH_SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String save(@RequestParam(value = PARAM_TRANSFER_ID) String transferId,
			@RequestParam(value = PARAM_RIVER_NAME) String riverName,
			@RequestParam(value = PARAM_CONTENT_TYPE) String contentType,
			@RequestParam(value = PARAM_CONTENT_DISPOSITION, required = false) String contentDisposition,
			@RequestParam(value = PARAM_CONTENT_DESCRIPTION, required = false) String contentDescription,
			@RequestParam(value = PARAM_TIMESTAMP) String timestamp,
			@RequestParam(value = PARAM_DATA) MultipartFile data) throws Exception {
		long receiveTime = System.currentTimeMillis();
		long sendTime = (Timestamp.valueOf(timestamp)).getTime();

		byte[] dataContent;
		try {
			dataContent = data.getBytes();
			_logger.info(new String(dataContent));
			//
			ObjectMapper mapper = new ObjectMapper();
			List<DataNode> list = mapper.readValue(dataContent,
					mapper.getTypeFactory().constructParametricType(List.class, DataNode.class));
			if(list != null){
				for(DataNode item : list){
					item.setTimestamp(new Date(sendTime));;
					dataDao.saveDataNode(item);
				}
			}

		} catch (Exception ioe) {
			_logger.error(ioe.getMessage());
			throw ioe;
		}

		String resultStr = "transferId: " + transferId + "\n" +
				"riverName: " + riverName 
				+ "\n" + 
				"contentType: " + contentType 
				+ "\n" + 
				"contentDisposition: " + contentDisposition 
				+ "\n" + 
				"contentDescription: " + contentDescription 
				+ "\n" + 
				"timestamp: " + Timestamp.valueOf(timestamp).getTime() + "\n" 
				+ "deviceId: "; 

		return resultStr;
	}
}
