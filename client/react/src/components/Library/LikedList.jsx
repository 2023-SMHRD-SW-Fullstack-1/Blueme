import React from 'react';
import SingleMusic from './SingleMusic.jsx';
import dummy from '../../dummy/MusicDummy.json';
import axios from 'axios';
// http://172.30.1.27:8104/likemusics/toggleLike
// -1이면 삭제, 0보다 크면 등록된 것.

const LikedList = () => {
    return (
        <div>
            {dummy.slice(0, 5).map((item) => (
                <SingleMusic key={item.id} item={item} />
            ))}
        </div>
    );
};

export default LikedList;
