package jetbrains.buildServer.artifacts;

import jetbrains.buildServer.artifacts.s3.S3Constants;
import jetbrains.buildServer.artifacts.s3.tree.S3ArtifactsListBrowser;
import jetbrains.buildServer.artifacts.s3.tree.S3Element;
import jetbrains.buildServer.util.browser.Element;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Nikita.Skvortsov
 * date: 08.02.2016.
 */
@Test
public class S3ElementTest {

  private S3ArtifactsListBrowser myS3Browser;

  @BeforeMethod
  public void setUp() throws Exception {
    myS3Browser = new S3ArtifactsListBrowser(new ArrayList<>());
  }

  public void testIsLeaf() throws Exception {
    Element el = new S3Element("path", new ExternalArtifact(null, "path", 0, S3Constants.S3_KEY, ""), myS3Browser);
    assertThat(el.isLeaf()).isFalse();

    el = new S3Element("path", new ExternalArtifact("url", "path", 0, S3Constants.S3_KEY, ""), myS3Browser);
    assertThat(el.isLeaf()).isTrue();
  }

  public void testPathAndName() throws Exception {
    Element el = new S3Element("f.txt", new ExternalArtifact(null, "f.txt", 0, S3Constants.S3_KEY, ""), myS3Browser);

    assertThat(el.getName()).isEqualTo("f.txt");
    assertThat(el.getFullName()).isEqualTo("f.txt");

    el = new S3Element("some/path/file.txt", new ExternalArtifact(null, "some/path/file.txt", 0, S3Constants.S3_KEY, ""), myS3Browser);

    assertThat(el.getName()).isEqualTo("file.txt");
    assertThat(el.getFullName()).isEqualTo("some/path/file.txt");
  }

  @Test(expectedExceptions = IllegalStateException.class)
  public void testIllegalThrowForInputStream() throws Exception {
    Element el = new S3Element("f.txt", new ExternalArtifact("url", "f.txt", 0, S3Constants.S3_KEY, ""), myS3Browser);
    assertThat(el.getSize()).isEqualTo(0);

    el.getInputStream();
  }
}
