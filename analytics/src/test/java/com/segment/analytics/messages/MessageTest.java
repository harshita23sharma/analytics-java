package com.segment.analytics.messages;

import com.segment.analytics.TestUtils.MessageBuilder;
import com.squareup.burst.BurstJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(BurstJUnit4.class) public class MessageTest {

  @Test public void missingUserIdAndAnonymousIdThrowsException(MessageBuilder builder) {
    try {
      builder.get().build();
      fail();
    } catch (IllegalStateException e) {
      assertThat(e).hasMessage("Either anonymousId or userId must be provided.");
    }
  }

  @Test public void aliasBuilder() {
    try {
      AliasMessage.builder(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("previousId cannot be null or empty.");
    }
  }

  @Test public void groupBuilder() {
    try {
      GroupMessage.builder(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("groupId cannot be null or empty.");
    }

    try {
      GroupMessage.builder("foo").traits(null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("Null traits");
    }
  }

  @Test public void identifyBuilder() {
    try {
      IdentifyMessage.builder().traits(null);
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("Null traits");
    }

    try {
      IdentifyMessage.builder().userId("foo").build();
    } catch (IllegalStateException e) {
      assertThat(e).hasMessage("Either userId or traits must be provided.");
    }
  }

  @Test public void screenBuilder() {
    try {
      ScreenMessage.builder("foo").properties(null).build();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("Null properties");
    }

    try {
      ScreenMessage.builder(null).userId("foo").build();
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("screen name cannot be null or empty.");
    }
  }

  @Test public void trackBuilder() {
    try {
      TrackMessage.builder(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertThat(e).hasMessage("event cannot be null or empty.");
    }

    try {
      TrackMessage.builder("foo").properties(null);
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessage("Null properties");
    }
  }
}
