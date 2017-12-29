import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car {

	// Habe das erstmal schnell geschrieben, damit wir wenigstens was zum laufen bekommen. Könnt ihr gerne verändern :)
	
	private Position position;
	private ImageView imgView = new ImageView();
	
	public Car(final Position position, final String filePath) {
		this.position = position;
		imgView.setImage(new Image(filePath));
		setImgViewPosition(position);
	}
	
	public void setPosition(final Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public ImageView getImageView() {
		return imgView;
	}
	
	public void setImgViewPosition(final Position position) {
		imgView.setLayoutX(position.getX());
		imgView.setLayoutY(position.getY());
	}
	
	public void move(final int speed) {
		position.setX(position.getX() + speed);
		imgView.setLayoutX(position.getX());
	}
	
}
