import React, { useState } from "react";
import Sidebar from "../../components/admin/Sidebar";
import WelcomeBanner from "../../partials/dashboard/WelcomeBanner";
import DashboardCard07 from "../../partials/dashboard/DashboardCard07";
import DashboardCard10 from "../../partials/dashboard/DashboardCard10";
import AddMusicPage from "../../components/admin/AddMusicPage";
import MainContent from "../../components/admin/MainContent";
import AddTheme from "../../components/admin/AddTheme";

const DashBoard = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  return (
    <div className="flex h-screen overflow-hidden">
      <Sidebar sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />
      {/* Content area */}
      {/* <MainContent /> */}

      {/* <AddMusicPage /> */}

      {/* AddThemePage */}
      <AddTheme />
    </div>
  );
};

export default DashBoard;
