/**
 * Wowza server software and all components Copyright 2006 - 2015, Wowza Media Systems, LLC, licensed pursuant to the Wowza Media Software End User License Agreement.
 */
package com.wowza.wms.plugin.multitrackvod;

import com.wowza.wms.application.*;
import com.wowza.wms.mediareader.h264.MediaReaderH264;
import com.wowza.wms.module.*;
import com.wowza.wms.stream.IMediaReader;
import com.wowza.wms.stream.IMediaReaderActionNotify;
import com.wowza.wms.stream.IMediaStream;

public class MultiTrackVOD extends ModuleBase implements IMediaReaderActionNotify
{
	public static final String MODULE_NAME = "ModuleMultiTrackVOD";
	public static final String PROP_NAME_PREFIX = "multiTrackVOD";
    public static final String PROPERTY_AUDIOINDEX = "audioindex";
    public static final String PROPERTY_VIDEOINDEX = "videoindex";
    public static final String PROPERTY_DATAINDEX = "dataindex";
    public static final String PROPERTY_INDEXES[] = {"audioindex", "videoindex", "dataindex"};

	public static final String MULTI_TRACK_VOD_RANDOM_ACCESS_READER_CLASS = "com.wowza.wms.plugin.collection.mediareaders.RandomAccessReaderMultiTrackVOD";
	
	public void onAppStart(IApplicationInstance appInstance)
	{
		String randomAccessReaderClass = appInstance.getMediaReaderProperties().getPropertyStr("randomAccessReaderClass");
		if(!MULTI_TRACK_VOD_RANDOM_ACCESS_READER_CLASS.equals(randomAccessReaderClass))
		{
			appInstance.getMediaReaderProperties().setProperty("randomAccessReaderClass", MULTI_TRACK_VOD_RANDOM_ACCESS_READER_CLASS);
		}
		appInstance.getProperties().setProperty(PROP_NAME_PREFIX + "RandomAccessReaderClass", randomAccessReaderClass);
		appInstance.addMediaReaderListener(this);
	}
	
	@Override
	public void onMediaReaderCreate(IMediaReader mediaReader)
	{
	}

	@Override
	public void onMediaReaderInit(IMediaReader mediaReader, IMediaStream stream)
	{
	}

	@Override
	public void onMediaReaderOpen(IMediaReader mediaReader, IMediaStream stream)
	{
		WMSProperties props = stream.getProperties();
		if(mediaReader instanceof MediaReaderH264)
		{
			int audioIndex = props.getPropertyInt(PROPERTY_AUDIOINDEX, -1);
			if (audioIndex > -1)
				((MediaReaderH264)mediaReader).setTrackIndexAudio(audioIndex);
			int videoIndex = props.getPropertyInt(PROPERTY_VIDEOINDEX, -1);
			if (videoIndex > -1)
				((MediaReaderH264)mediaReader).setTrackIndexVideo(videoIndex);
			int dataIndex = props.getPropertyInt(PROPERTY_DATAINDEX, -1);
			if (dataIndex > -1)
				((MediaReaderH264)mediaReader).setTrackIndexData(dataIndex);
		}
	}

	@Override
	public void onMediaReaderExtractMetaData(IMediaReader mediaReader, IMediaStream stream)
	{
	}

	@Override
	public void onMediaReaderClose(IMediaReader mediaReader, IMediaStream stream)
	{
	}
}
