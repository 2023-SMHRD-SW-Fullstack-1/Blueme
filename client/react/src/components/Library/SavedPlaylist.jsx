import React from 'react'
import dummy from '../data/PlaylistDummy.json'
const SavedPlaylist = () => {
  return (
    <div className="flex flex-row items-center">
         {dummy.map((item) => {
                return (
                    <div key={item.id} className="flex flex-col items-center p-2">
                        {/* 1. 플레이리스트 커버 */}
                        <img src={item.coverImage} alt="album cover" className="w-[100px] h-auto object-cover rounded-lg" />
                        {/* 2. 플레이리스트명 */}
                        <span className="text-xs text-gray-500 uppercase font-medium py-2 ">{item.title}</span>
                    </div>
                );
            })}
    </div>
  )
}

export default SavedPlaylist