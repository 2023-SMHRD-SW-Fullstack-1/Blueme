import React from "react";
import logo from "../img/logo.png";

const songs = [
  { id: 1, title: "Song Title 1", artist: "Artist Name 1", cover: "https://via.placeholder.com/150" },
  { id: 2, title: "Song Title 2", artist: "Artist Name 2", cover: "https://via.placeholder.com/150" },
  { id: 2, title: "Song Title 2", artist: "Artist Name 2", cover: "https://via.placeholder.com/150" },
  { id: 2, title: "Song Title 2", artist: "Artist Name 2", cover: "https://via.placeholder.com/150" },
  { id: 2, title: "Song Title 2", artist: "Artist Name 2", cover: "https://via.placeholder.com/150" },
  { id: 2, title: "Song Title 2", artist: "Artist Name 2", cover: "https://via.placeholder.com/150" },
];

const Mymusicplaylist = () => {
  return (
    <div className="p-10 bg-custom-blue text-custom-white text-white min-h-screen items-center ">
      <h1 className="text-4xl font-bold mb-10">My Playlist</h1>
      <div className=" grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        {songs.map((song) => (
          <div key={song.id} className="bg-gray-800 rounded-lg shadow-lg overflow-hidden">
            <img src={logo} alt="Album Cover" className="w-full h-auto object-cover" />
            <div className="p-3">
              <h2 className="text-xl font-bold mb-2 truncate">{song.title}</h2>
              <p className="text-gray-400  truncate">{song.artist}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Mymusicplaylist;
