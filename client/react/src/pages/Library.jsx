import React from "react";
import LikedList from "../components/Library/LikedList";
import SavedPlaylist from "../components/Library/SavedPlaylist";

const Library = () => {
  return (
    <>
      <LikedList />
      <p className="text-left">내가 저장한 플레이리스트</p>
      <SavedPlaylist />
    </>
  );
};

export default Library;
