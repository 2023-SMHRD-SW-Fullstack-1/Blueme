import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const Theme = () => {
  const [themes, setThemes] = useState([]);

  useEffect(() => {
    const fetchThemes = async () => {
      try {
        const response = await axios.get("/theme/themelists");
        setThemes(response.data);
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchThemes();
  }, []);

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-screen text-byte text-custom-white font-semibold tracking-tighter">
      <br/>
      <div class="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-2 mt-[120px] text-custom-black">
        {themes.map((theme, i) => {
          const themeImage = theme?.image || `/theme/themelists//${i + 1}`;
          const themeName = theme?.title;
          const themeId = theme?.id || i + 1;

          const handleButtonClick = () => {
            localStorage.setItem("themeImage", themeImage);
            localStorage.setItem("themeName", themeName);

            localStorage.setItem("themeId", themeId);
          };

          return (
            <button
              key={i}
              class="flex flex-col items-center justify-center rounded-xl shadow-md"
              onClick={handleButtonClick}
            >
            <Link to="/Playlist" className="mb-10">
                <img src={themeImage} alt="" class="rounded-lg w-[180px] h-[170px] object-cover mt-11" />
              <h5 class="font-semibold mt-5">{themeName}</h5></Link>
            </button>
          );
        })}
      </div>
    </div>
  );
};

export default Theme;
