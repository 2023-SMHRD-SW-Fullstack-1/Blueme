import React from 'react';
import { Link } from 'react-router-dom';
import LikedList from '../components/Library/LikedList';
import SavedPlaylist from '../components/Library/SavedPlaylist';
// const paramValue = 'http://172.30.1.27:8104/music/1';


const Library = () => {
    return (
        <div className="bg-custom-blue text-custom-white h-full pt-20">
            <div className="flex">
                <span className="text-left text-2xl ml-2">내가 좋아요 누른 곡들</span>

                <button className="ml-auto text-custom-gray mr-2">
                    <Link to="/Playlist">더보기</Link>
                </button>
            </div>
            <LikedList />
            <p className="text-left text-2xl ml-2 pt-5">저장한 플레이리스트</p>
            <SavedPlaylist />
            <Link to="/MusicPlayer">
                <button>뮤직플레이어로 이동</button>
            </Link>
            {/* 지희 - 음원 데이터 통신 시도 중 (0906) */}
            {/* <Link to={`/MusicPlayer?url=${paramValue}`}>
                <button>뮤직플레이어로 이동</button>
            </Link> */}
        </div>
    );
};

export default Library;
