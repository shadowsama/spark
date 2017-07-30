/**
  * Created by shadow on 2017/7/30 0030.
  */

import org.junit.Test
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito.{mock, never, verify, when}
object mock_1 {


  @Test
  def main1(): Unit = {
    // mock creation
    var  mockedList = mock(classOf[java.util.List[String]]);

    // using mock object - it does not throw any "unexpected interaction" exception
    mockedList.add("one");
    mockedList.clear();

    // selective, explicit, highly readable verification
    verify(mockedList).add("one");
    verify(mockedList).clear();
  }

}
