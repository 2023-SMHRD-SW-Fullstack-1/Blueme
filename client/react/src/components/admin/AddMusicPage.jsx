import React, { useState } from "react";
import axios from "axios";

const AddMusicPage = () => {
  const [files, setFiles] = useState([]);

  const handleFileChange = (e) => {
    setFiles(e.target.files);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    console.log("asd");
    // 여러 파일이므로 각각의 파일에 대해 append 실행
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }
    console.log("abc");
    try {
      const response = await axios.post(
        "http://localhost:8104/music/admin/addmusic",
        formData
      );
      console.log("eee");
      console.log(response.data); // log the response
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="relative flex flex-col flex-1 overflow-y-auto overflow-x-hidden">
      <main>
        <div className="px-4 sm:px-6 lg:px-8 py-14 w-full max-w-9xl mx-auto my-6">
          {/* Cards */}
          <h1 className="text-2xl md:text-4xl lg:text-4xl font-bold mb-4 text-[#1E293B]">
            음악 업로드
          </h1>

          <form onSubmit={handleSubmit} className="my-10">
            {/* multiple 속성 추가하여 다중 선택 가능하게 함 */}
            <input type="file" onChange={handleFileChange} multiple />

            <button
              type="submit"
              className="
    mt-4 bg-[#1E293B] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >
              업로드
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default AddMusicPage;
