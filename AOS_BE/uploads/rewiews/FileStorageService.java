@Service
public class FileStorageService {

    private final String uploadDir = "uploads/reviews";

    public List<String> saveFiles(List<MultipartFile> files) {
        if (files == null) return new ArrayList<>();
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                try {
                    Path filePath = Paths.get(uploadDir, fileName).toAbsolutePath().normalize();
                    Files.createDirectories(filePath.getParent());
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    urls.add("/uploads/reviews/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return urls;
    }

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            Path filePath = Paths.get(uploadDir, fileName).toAbsolutePath().normalize();
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/reviews/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
