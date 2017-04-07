package com.martsforever.owa.leancloud_push_practice;

import com.martsforever.owa.leancloud_push_practice.handle.Handle;
import com.martsforever.owa.leancloud_push_practice.handle.HandleFriendsInvite;
import com.martsforever.owa.leancloud_push_practice.handle.Message;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHandle() throws Exception {
        Handle handle;
        Message message = new Message();
        message.setHandleClassName(HandleFriendsInvite.class.getName());

        handle = (Handle) Class.forName(message.getHandleClassName()).newInstance();
        handle.handle(message);
    }
}