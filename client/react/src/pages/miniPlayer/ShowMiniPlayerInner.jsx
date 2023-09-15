/*
작성자: 이지희
날짜(수정포함): 2023-09-13
설명: 미니플레이어 보여주는 컴포넌트
*/
import { useLocation, Routes, Route } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';

import MiniPlayer from './MiniPlayer';
import { setShowMiniPlayer, setPlayingStatus } from '../../store/music/musicActions'; // 수정된 부분

const ShowMiniPlayerInner = () => {
  const location = useLocation();
  const dispatch = useDispatch();

  useEffect(() => {
    const pathsToShowMiniPlayer = ['/', '/library', '/Playlist','/LikedPlaylist', '/RecPlayList', '/Theme'];

    if (pathsToShowMiniPlayer.includes(location.pathname)) {
      dispatch(setShowMiniPlayer(true));
    } else {
      dispatch(setShowMiniPlayer(false));
      dispatch(setPlayingStatus(false))
    }
  }, [location]);

  const showMiniPlayer = useSelector((state) => state.showMiniPlayer);
  const currentSongId = useSelector((state) => state.currentSongId);

  return showMiniPlayer ? <MiniPlayer item={currentSongId} /> : null
  // return null;
};

export default ShowMiniPlayerInner;
