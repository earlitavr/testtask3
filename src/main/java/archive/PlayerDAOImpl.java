package archive;

import com.game.entity.PlayerEntity;
import archive.PlayerDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<PlayerEntity> getAllPlayers() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from player").list();
    }

    @Override
    @Transactional
    public List<PlayerEntity> getPlayersById() {
        return null;
    }
}
