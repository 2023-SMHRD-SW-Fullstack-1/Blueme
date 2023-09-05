import React from 'react';
import SingleMusic from './SingleMusic.jsx';
import dummy from '../../dummy/MusicDummy.json';

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
