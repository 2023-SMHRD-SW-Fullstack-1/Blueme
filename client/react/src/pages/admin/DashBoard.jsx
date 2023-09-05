import React, { useState } from "react";
import Sidebar from "../../components/admin/Sidebar";

const DashBoard = () => {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  return (
    <div className="flex h-screen overflow-hidden">
      <Sidebar />
    </div>
  );
};

export default DashBoard;
