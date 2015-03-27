package com.segment.analytics.messages;

import com.google.auto.value.AutoValue;
import com.segment.analytics.internal.gson.AutoGson;
import java.util.Date;
import java.util.UUID;

import static com.segment.analytics.internal.Utils.isNullOrEmpty;

/**
 * The alias message is used to merge two user identities, effectively connecting two sets of user
 * data as one. This is an advanced method, but it is required to manage user identities
 * successfully in some of our integrations.
 * <p/>
 * Use {@link #builder} to construct your own instances.
 *
 * @see <a href="https://segment.com/docs/spec/alias/">Alias</a>
 */
@AutoValue @AutoGson public abstract class AliasMessage implements Message {

  /**
   * Start building an {@link AliasMessage} instance.
   *
   * @param previousId The previous unique identifier for the user. See the Previous ID field
   * docs for more detail.
   * @throws IllegalArgumentException if the previousId is null or empty
   */
  public static Builder builder(String previousId) {
    return new Builder(previousId);
  }

  public abstract String previousId();

  public Builder toBuilder() {
    return new Builder(this);
  }

  /** Fluent API for creating {@link AliasMessage} instances. */
  public static class Builder extends MessageBuilder<AliasMessage, Builder> {
    private String previousId;

    private Builder(AliasMessage alias) {
      previousId = alias.previousId();
    }

    private Builder(String previousId) {
      if (isNullOrEmpty(previousId)) {
        throw new IllegalArgumentException("previousId cannot be null or empty.");
      }
      this.previousId = previousId;
    }

    @Override protected AliasMessage realBuild() {
      return new AutoValue_AliasMessage(Type.ALIAS, UUID.randomUUID(), new Date(), context,
          anonymousId, userId, integrations, previousId);
    }

    @Override Builder self() {
      return this;
    }
  }
}
