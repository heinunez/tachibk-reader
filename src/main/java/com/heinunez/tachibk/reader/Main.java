import com.heinunez.tachibk.reader.Backup;
import com.heinunez.tachibk.reader.BackupChapter;


void main(String... args) {

    if(args == null || args.length < 1){
        System.out.println("select a bk file");
        System.exit(0);
    }


    try (var is = new FileInputStream(args[0]);
        var gis = new GZIPInputStream(is)
    ) {
        var back = Backup.parseFrom(gis);
        back.getBackupMangaList().forEach(manga -> {
            System.out.printf("title: %s\n", manga.getTitle());

            manga.getChaptersList()
                .stream()
                .filter(BackupChapter::getRead)
                .findFirst()
                .ifPresent(ch ->
                    System.out.printf("last chapter: %.1f\n", ch.getChapterNumber())
                );

            System.out.println("---");
        });
    } catch (FileNotFoundException _) {
        System.out.println("backup file not found");
    } catch (IOException e) {
        System.out.println("invalid backup file");
    }
}