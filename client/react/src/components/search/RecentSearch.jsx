import { faArrowRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

// 음악 재생 관련 리덕스
import {
  setCurrentSongId,
  setPlayingStatus,
} from "../../store/music/musicActions";

const RecentSearch = ({ item }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  // 사용자 id
  const user = useSelector((state) => state.memberReducer.user);
  const userId = user.id;

  // 재생id
  const currentSongId = useSelector(
    (state) => state.musicReducer.currentSongId
  );

  // 최근검색 등록
  const uploadRecentMusic = () => {
    axios
      .put(`${process.env.REACT_APP_API_BASE_URL}/search`, {
        userId: userId,
        musicId: item.musicId,
      })
      .then((response) => {
        dispatch(setCurrentSongId(item.musicId));
        dispatch(setPlayingStatus(true));
        navigate(`/MusicPlayer/${currentSongId}`);
      })
      .catch((error) => {
        console.error(error);
      });
  };
  return (
    <div
      className="w-full pr-3 flex justify-between items-center gap-x-4"
      onClick={uploadRecentMusic}
    >
      <img
        className="rounded-full m-2 w-14 flex-none"
        src={"data:image/;base64," + item.img}
        alt="no img"
      />
      <div className="grow">
        <p>{item.title}</p>
        <p className="text-[8px] text-[#949494]">{item.artist}</p>
      </div>
      <FontAwesomeIcon
        className="flex-none"
        icon={faArrowRight}
        style={{ color: "#ebebeb" }}
      />
    </div>
  );
};

export default RecentSearch;
