package com.oup.k8job.bl;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("springManagedDeleteFilesBL")
public class DeleteFilesBL {

	@Value("${com.oup.numofdaystokeep}")
	private String daysToDelete;

	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public void deleteOldFilesFolders() {
		ArrayList<String> rootFolders = new ArrayList<>();
		long cutoff = System.currentTimeMillis() - (Integer.parseInt(daysToDelete) * 24 * 60 * 60 * 1000);
		rootFolders.add("/home/wolverine/logs");
		//rootFolders.add("/IN");
		//rootFolders.add("/OUT");

		// rootFolders.add("C:\\SKG\\PERSONAL\\abc");
		rootFolders.forEach(rootfolder -> {

			FileUtils.listFiles(new File(rootfolder), new AgeFileFilter(cutoff), TrueFileFilter.INSTANCE)
					.forEach(item -> {
						logger.debug("Deleting file --> " + item.getName() + " from " + item.getParent());
						FileUtils.deleteQuietly(item);
					});
		});
	}

}
