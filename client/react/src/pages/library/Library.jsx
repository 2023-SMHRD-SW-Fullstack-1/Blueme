import React from 'react';
import { Link } from 'react-router-dom';
import LikedList from '../../components/Library/LikedList';
import SavedPlaylist from '../../components/Library/SavedPlaylist';

const Library = () => {
    return (
        <div className="bg-custom-blue text-custom-white h-full pt-20">
            <div className="flex">
                <span className="text-left text-2xl ml-2">내가 좋아요 누른 곡들</span>

                <button className="ml-auto text-custom-gray mr-2">
                    <Link to="/LibraryPlaylist">더보기</Link>
                </button>
            </div>
            <LikedList />
            <p className="text-left text-2xl ml-2 pt-5">저장한 플레이리스트</p>
            <SavedPlaylist />
        </div>
    );
};

export default Library;
