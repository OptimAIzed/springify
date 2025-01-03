import styles from "./LeftPanel.module.css";

import CustomIcon from "../CustomIcon/CustomIcon";

//Icons
import MenuIcon from '@mui/icons-material/Menu';
import HistoryIcon from '@mui/icons-material/History';
import GitHubIcon from '@mui/icons-material/GitHub';

function LeftPanel({ theme, history, setHistory }) {
  return (
    <div className={styles.container}>
      <div className={styles.top}>
        <CustomIcon theme={theme} icon={MenuIcon} size={38} />
        <hr />
        <div onClick={() => setHistory(!history)}><CustomIcon theme={theme} icon={HistoryIcon} size={40} /></div>
      </div>
      <div className={styles.bottom}>
        <a href="https://github.com/OptimAIzed/springify" target="_blank"><CustomIcon theme={theme} icon={GitHubIcon} size={34} /></a>
      </div>
    </div>
  )
}

export default LeftPanel