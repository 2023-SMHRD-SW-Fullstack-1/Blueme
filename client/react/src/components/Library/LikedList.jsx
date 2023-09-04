import React from 'react';
import Music from './Music';

const LikedList = () => {
    return (
        <div>
            <p className="text-left">내가 좋아요 누른 곡들</p>
            <Music />
        </div>
    );
};

export default LikedList;
