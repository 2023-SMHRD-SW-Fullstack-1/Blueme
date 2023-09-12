/*
작성자: 신지훈
날짜: 2023-09-11
설명: 테마 불러오기
*/

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const Theme = () => {
  const [themes, setThemes] = useState([]);

  useEffect(() => {
    const fetchThemes = async () => {
      try {
        const response = await axios.get("/theme/themelists");
        console.log(response.data);
        setThemes(response.data);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchThemes();
  }, []);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-screen text-byte text-custom-white font-semibold tracking-tighter">
      <br />
      <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-2 mt-[120px] text-custom-black">
        {themes.map((theme, i) => {
          const themeImage = theme?.img;
          const themeName = theme?.title;
          const themeId = theme?.id || i + 1;

          const handleButtonClick = () => {
            localStorage.setItem("themeImage", themeImage);
            localStorage.setItem("themeName", themeName);

            localStorage.setItem("themeId", themeId);

            console.log("Stored in local storage:");
            console.log("Theme image:", localStorage.getItem("themeImage"));
          };

          return (
            <button key={i} class="flex flex-col  rounded-xl shadow-md" onClick={handleButtonClick}>
              <Link to="/Playlist" className="mb-10">
                <div class="flex flex-col items-center justify-center text-center text-custom-white">
                  <img
                    src={"data:image/;base64," + themeImage}
                    alt=""
                    class="rounded-lg w-[80%] h-[auto] object-cover mt-11"
                  />
                  <h5 class="font-semibold mt-5">{themeName}</h5>
                </div>
              </Link>
            </button>
          );
        })}
      </div>
    </div>
  );
};

export default Theme;
