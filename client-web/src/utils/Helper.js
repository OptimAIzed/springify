import { format } from "date-fns";

export const groupByDate = (projects) => {
  const grouped = {};

  projects.forEach((project) => {
    const date = format(new Date(project.creationDate), "EEEE, d MMMM yyyy");

    if (!grouped[date]) {
      grouped[date] = [];
    }

    grouped[date].push({
      time: format(new Date(project.creationDate), "HH:mm"), 
      title: `Artifact: ${project.artifactId}, Group: ${project.groupId}`,
      dependencies: project.dependencies
        ? `Dependencies: ${project.dependencies}`
        : "No Dependencies affected",
      img: project.image,
    });
  });

  return Object.entries(grouped).map(([date, entries]) => ({
    date,
    entries,
  }));
};
