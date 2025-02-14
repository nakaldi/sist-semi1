package kims.semi1.config;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResourceManager {
	private static final HashMap<String, ImageIcon> iconCache = new HashMap<>();
	private static final HashMap<String, Image> imageCache = new HashMap<>();

	private ResourceManager() {
	} // 싱글톤 패턴 - 객체 생성 방지

	// ✅ 🔹 JAR 내부에서 실행 시 리소스 로드
	private static InputStream getResourceStream(String fileName) {
		return ResourceManager.class.getClassLoader().getResourceAsStream(fileName);
	}

	// ✅ 🔹 Local 실행일 경우 (IDE 실행 시)
	private static File getLocalFile(String fileName) {
		return new File("resources/" + fileName);
	}

	// ✅ 🔹 배경 이미지 로드 (JAR & Local 실행 모두 지원)
	public static Image getImage(String fileName) {
		if (imageCache.containsKey(fileName)) {
			return imageCache.get(fileName);
		}

		Image img = loadImage(fileName);
		if (img != null) {
			imageCache.put(fileName, img);
		}
		return img;
	}

	private static Image loadImage(String fileName) {
		try {
			// 🔹 JAR 실행인 경우: InputStream 사용
			InputStream is = getResourceStream(fileName);
			if (is != null) {
				return ImageIO.read(is);
			}

			// 🔹 Local 실행인 경우: 파일 직접 읽기
			File file = getLocalFile(fileName);
			if (file.exists()) {
				return ImageIO.read(file);
			}

			System.out.println("리소스를 찾을 수 없습니다: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ✅ 🔹 아이콘 로드 (JAR & Local 실행 지원)
	public static ImageIcon getIcon(String fileName) {
		if (iconCache.containsKey(fileName)) {
			return iconCache.get(fileName);
		}

		ImageIcon icon = loadIcon(fileName);
		if (icon != null) {
			iconCache.put(fileName, icon);
		}
		return icon;
	}

	private static ImageIcon loadIcon(String fileName) {
		try {
			// 🔹 JAR 실행 시
			InputStream is = getResourceStream(fileName);
			if (is != null) {
				byte[] bytes = is.readAllBytes();
				return new ImageIcon(bytes);
			}

			// 🔹 Local 실행 시
			File file = getLocalFile(fileName);
			if (file.exists()) {
				return new ImageIcon(file.getAbsolutePath());
			}

			System.out.println("아이콘을 찾을 수 없습니다: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
