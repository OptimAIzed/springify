import styles from "./CustomIcon.module.css";

function CustomIcon({ theme, icon: Icon, size }) {
  return (
    <div className={styles.container}>
      <Icon sx={{
        color: theme == "light" ? 'black' : 'white', fontSize: size, '&:hover': {
          color: theme == "light" ? '#434343' : '#e3e3e3',
        },
      }} />
    </div>
  )
}

export default CustomIcon