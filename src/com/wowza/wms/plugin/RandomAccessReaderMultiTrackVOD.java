/*
 * This code and all components (c) Copyright 2006 - 2018, Wowza Media Systems, LLC. All rights reserved.
 * This code is licensed pursuant to the Wowza Public License version 1.0, available at www.wowza.com/legal.
 */
package com.wowza.wms.plugin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.wowza.io.DirectRandomAccessReader;
import com.wowza.io.IRandomAccessReader;
import com.wowza.io.ITrackRandomAccessReaderPerformance;
import com.wowza.util.HTTPUtils;
import com.wowza.util.IOPerformanceCounter;
import com.wowza.util.StringUtils;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.stream.IMediaReader;
import com.wowza.wms.stream.IMediaStream;

public class RandomAccessReaderMultiTrackVOD implements IRandomAccessReader, ITrackRandomAccessReaderPerformance
{
	public static final String CLASS_NAME = "RandomAccessReaderMultiTrackVOD";

	private IRandomAccessReader reader = null;

	private IRandomAccessReader getReader(IApplicationInstance appInstance)
	{
		IRandomAccessReader localReader = new DirectRandomAccessReader();

		String readerClassName = appInstance.getProperties().getPropertyStr(ModuleMultiTrackVOD.PROP_NAME_PREFIX + "RandomAccessReaderClass", ModuleMultiTrackVOD.MULTI_TRACK_VOD_RANDOM_ACCESS_READER_CLASS);
		if (!readerClassName.equals(ModuleMultiTrackVOD.MULTI_TRACK_VOD_RANDOM_ACCESS_READER_CLASS))
		{
			try
			{
				Class readerClass = Class.forName(readerClassName);
				if (readerClass != null)
					localReader = (IRandomAccessReader)readerClass.newInstance();
			}
			catch (Exception e)
			{
				WMSLoggerFactory.getLoggerObj(appInstance).error(CLASS_NAME + ".getReader() Cannot load randomAccessReaderClass :" + readerClassName);
			}
		}
		return localReader;
	}

	@Override
	public void init(IApplicationInstance appInstance, IMediaStream stream, String basePath, String mediaName, String mediaExtension)
	{
		reader = getReader(appInstance);
		try
		{
			System.out.println("Decoding mediaName: " + mediaName);
			
			mediaName = mediaName.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			mediaName = mediaName.replaceAll("\\+", "%2B");
			mediaName = URLDecoder.decode(mediaName, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
		}
		catch (Exception e)
		{
			WMSLoggerFactory.getLoggerObj(appInstance).warn(CLASS_NAME + ".init() Cannot url decode media name: " + mediaName + ". Error: " + e.getMessage());
		}
//		System.out.println("RandomAccessReaderMultiTrackVOD.init() mediaName: " + mediaName);
		if (appInstance.getMediaReaderContentType(mediaExtension) != IMediaReader.CONTENTTYPE_MEDIALIST) // SMIL
		{
			Map<String, String> queryMap = new HashMap<String, String>();
			String sessionQueryStr = "";
			if (stream.getClient() != null && !StringUtils.isEmpty(stream.getClient().getQueryStr()))
				sessionQueryStr = stream.getClient().getQueryStr();
			if (stream.getHTTPStreamerSession() != null && !StringUtils.isEmpty(stream.getHTTPStreamerSession().getQueryStr()))
				sessionQueryStr = stream.getHTTPStreamerSession().getQueryStr();
			if (stream.getRTPStream() != null && stream.getRTPStream().getSession() != null && !StringUtils.isEmpty(stream.getRTPStream().getSession().getQueryStr()))
				sessionQueryStr = stream.getRTPStream().getSession().getQueryStr();

			if (!sessionQueryStr.equals(""))
				queryMap.putAll(HTTPUtils.splitQueryStr(sessionQueryStr));

			if (!StringUtils.isEmpty(stream.getQueryStr()))
				queryMap.putAll(HTTPUtils.splitQueryStr(stream.getQueryStr()));

			int queryIdx = mediaName.indexOf("?");
			if (queryIdx >= 0)
			{
				String queryStr = mediaName.substring(queryIdx + 1);
				queryMap.putAll(HTTPUtils.splitQueryStr(queryStr));
				mediaName = mediaName.substring(0, queryIdx);
			}

			if (!queryMap.isEmpty())
			{
				for (int i = 0; i < ModuleMultiTrackVOD.PROPERTY_INDEXES.length; i++)
				{
					String indexStr = ModuleMultiTrackVOD.PROPERTY_INDEXES[i];
					if (queryMap.containsKey(indexStr))
					{
						try
						{
							int index = Integer.parseInt(queryMap.get(indexStr));
							stream.getProperties().setProperty(indexStr, index);
						}
						catch (Exception exception)
						{

						}
					}
				}
			}
		}

		reader.init(appInstance, stream, basePath, mediaName, mediaExtension);
	}

	@Override
	public void setStreamIOTracker(IOPerformanceCounter ioPerforamnceCounter)
	{
		if (reader instanceof ITrackRandomAccessReaderPerformance)
			((ITrackRandomAccessReaderPerformance)reader).setStreamIOTracker(ioPerforamnceCounter);
	}

	@Override
	public void setClientIOTracker(IOPerformanceCounter ioPerforamnceCounter)
	{
		if (reader instanceof ITrackRandomAccessReaderPerformance)
			((ITrackRandomAccessReaderPerformance)reader).setClientIOTracker(ioPerforamnceCounter);
	}

	@Override
	public void open() throws IOException
	{
		reader.open();
	}

	@Override
	public void close() throws IOException
	{
		reader.close();
	}

	@Override
	public boolean isOpen()
	{
		return reader.isOpen();
	}

	@Override
	public long getFilePointer()
	{
		return reader.getFilePointer();
	}

	@Override
	public void seek(long pos)
	{
		reader.seek(pos);
	}

	@Override
	public int read(byte[] buf, int off, int size)
	{
		return reader.read(buf, off, size);
	}

	@Override
	public int getDirecton()
	{
		return reader.getDirecton();
	}

	@Override
	public void setDirecton(int directon)
	{
		reader.setDirecton(directon);
	}

	@Override
	public String getBasePath()
	{
		return reader.getBasePath();
	}

	@Override
	public String getMediaName()
	{
		return reader.getMediaName();
	}

	@Override
	public String getMediaExtension()
	{
		return reader.getMediaExtension();
	}

	@Override
	public boolean exists()
	{
		return reader.exists();
	}

	@Override
	public long lastModified()
	{
		return reader.lastModified();
	}

	@Override
	public long length()
	{
		return reader.length();
	}

	@Override
	public String getPath()
	{
		return reader.getPath();
	}
}
