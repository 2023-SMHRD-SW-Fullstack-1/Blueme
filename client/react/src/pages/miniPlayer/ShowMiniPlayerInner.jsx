/*
작성자: 이지희
날짜(수정포함): 2023-09-20
설명: 미니플레이어
*/
import { useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';

import MiniPlayer from './MiniPlayer';
import { setShowMiniPlayer, setPlayingStatus } from '../../store/music/musicActions'; // 수정된 부분

const ShowMiniPlayerInner = () => {
  const location = useLocation();
  const dispatch = useDispatch();

  // 미니플레이어 UI on/off & 음악재생 on/off
  useEffect(() => {
    const pathsToShowMiniPlayer = ['/', '/library', '/Playlist','/LikedPlaylist', '/Theme', '/ThemePlaylist', '/search'];
    
    if (pathsToShowMiniPlayer.includes(location.pathname)) { // 미니플레이어 O
      dispatch(setShowMiniPlayer(true));
    } else if (location.pathname.includes('/MusicPlayer')) {
      dispatch(setShowMiniPlayer(false));
    } else if (location.pathname.includes('/RecPlayListDetail') || location.pathname.includes('/SavedPlaylistDetail')) {
      dispatch(setShowMiniPlayer(true));
    } else { // 미니플레이어 X 재생 X
      dispatch(setShowMiniPlayer(false));
      dispatch(setPlayingStatus(false))
    } 
  }, [location]);

  const showMiniPlayer = useSelector((state) => state.musicReducer.showMiniPlayer);

  return showMiniPlayer? <MiniPlayer /> : null;

};

export default ShowMiniPlayerInner;
